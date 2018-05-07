package ru.r2cloud.satellite;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import ru.r2cloud.RtlSdrLock;
import ru.r2cloud.TestConfiguration;
import ru.r2cloud.cloud.R2CloudService;
import ru.r2cloud.model.Satellite;
import ru.r2cloud.util.Clock;
import ru.r2cloud.util.ThreadPoolFactory;

public class SchedulerTest {

	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();

	private TestConfiguration config;
	private SatelliteDao satelliteDao;
	private ObservationFactory factory;
	private ThreadPoolFactory threadPool;
	private R2CloudService r2cloudService;
	private ScheduledExecutorService executor;
	private APTObservation observation;
	private Clock clock;
	private String id;

	@Test
	public void testSuccess() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss, SSS");
		Date current = sdf.parse("2017-10-23 00:00:00, 000");
		Date start = sdf.parse("2017-10-23 07:00:00, 000");
		Date end = sdf.parse("2017-10-23 08:00:00, 000");
		when(observation.getStart()).thenReturn(start);
		when(observation.getEnd()).thenReturn(end);
		when(clock.millis()).thenReturn(current.getTime());
		Scheduler s = new Scheduler(config, satelliteDao, new RtlSdrLock(), factory, threadPool, clock, r2cloudService);
		s.start();

		verify(executor).schedule(any(Runnable.class), eq(TimeUnit.HOURS.toMillis(7)), eq(TimeUnit.MILLISECONDS));
		verify(executor).schedule(any(Runnable.class), eq(TimeUnit.HOURS.toMillis(8)), eq(TimeUnit.MILLISECONDS));
	}

	@Test
	public void testListenToConfiguration() throws Exception {
		config.setProperty("satellites.enabled", false);
		Scheduler s = new Scheduler(config, satelliteDao, new RtlSdrLock(), factory, threadPool, clock, r2cloudService);
		s.start();

		verify(executor, never()).schedule(any(Runnable.class), anyLong(), any());

		config.setProperty("satellites.enabled", true);
		config.update();

		verify(executor, times(2)).schedule(any(Runnable.class), anyLong(), any());
	}

	@Test
	public void testLifecycle() {
		Scheduler s = new Scheduler(config, satelliteDao, new RtlSdrLock(), factory, threadPool, clock, r2cloudService);
		s.start();
		s.start();

		verify(executor, times(2)).schedule(any(Runnable.class), anyLong(), any());
		assertNotNull(s.getNextObservation(id));

		s.stop();
		s.stop();
	}

	@Before
	public void start() throws Exception {
		config = new TestConfiguration(tempFolder);
		config.setProperty("satellites.enabled", true);

		clock = mock(Clock.class);
		id = UUID.randomUUID().toString();
		satelliteDao = mock(SatelliteDao.class);
		factory = mock(ObservationFactory.class);
		observation = mock(APTObservation.class);
		threadPool = mock(ThreadPoolFactory.class);
		executor = mock(ScheduledExecutorService.class);
		r2cloudService = mock(R2CloudService.class);
		when(threadPool.newScheduledThreadPool(anyInt(), any())).thenReturn(executor);
		when(factory.create(any(), any())).thenReturn(observation);
		when(observation.getStart()).thenReturn(new Date());
		when(observation.getEnd()).thenReturn(new Date());
		when(satelliteDao.findAll()).thenReturn(Collections.singletonList(createSatellite(id)));
		when(executor.awaitTermination(anyLong(), any())).thenReturn(true);
	}

	private static Satellite createSatellite(String id) {
		Satellite result = new Satellite();
		result.setId(id);
		return result;
	}

}
