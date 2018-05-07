package ru.r2cloud.model;

import java.io.File;
import java.util.Date;

public class ObservationResult {

	private String id;
	private File wavPath;
	private String gain;
	private String channelA;
	private String channelB;
	private String aURL;
	private String bURL;
	private Date start;
	private Date end;
	private String spectogramURL;
	private Long numberOfDecodedPackets;
	private String satelliteId;

	private File aPath;
	private File spectogramPath;
	
	public String getSatelliteId() {
		return satelliteId;
	}
	
	public void setSatelliteId(String satelliteId) {
		this.satelliteId = satelliteId;
	}

	public File getaPath() {
		return aPath;
	}

	public void setaPath(File aPath) {
		this.aPath = aPath;
	}

	public File getSpectogramPath() {
		return spectogramPath;
	}

	public void setSpectogramPath(File spectogramPath) {
		this.spectogramPath = spectogramPath;
	}

	public Long getNumberOfDecodedPackets() {
		return numberOfDecodedPackets;
	}

	public void setNumberOfDecodedPackets(Long numberOfDecodedPackets) {
		this.numberOfDecodedPackets = numberOfDecodedPackets;
	}

	public String getSpectogramURL() {
		return spectogramURL;
	}

	public void setSpectogramURL(String spectogramURL) {
		this.spectogramURL = spectogramURL;
	}

	public String getGain() {
		return gain;
	}

	public void setGain(String gain) {
		this.gain = gain;
	}

	public String getChannelA() {
		return channelA;
	}

	public void setChannelA(String channelA) {
		this.channelA = channelA;
	}

	public String getChannelB() {
		return channelB;
	}

	public void setChannelB(String channelB) {
		this.channelB = channelB;
	}

	public String getaURL() {
		return aURL;
	}

	public void setaURL(String aURL) {
		this.aURL = aURL;
	}

	public String getbURL() {
		return bURL;
	}

	public void setbURL(String bURL) {
		this.bURL = bURL;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public File getWavPath() {
		return wavPath;
	}

	public void setWavPath(File wavPath) {
		this.wavPath = wavPath;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

}
