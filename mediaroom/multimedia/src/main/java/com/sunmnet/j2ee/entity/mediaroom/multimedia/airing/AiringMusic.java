package com.sunmnet.j2ee.entity.mediaroom.multimedia.airing;

import com.sunmnet.j2ee.entity.base.OnlyIdEntity;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/*
 * 代码生成工具自动生成
 * wanghz@sunmnet.com
 *
 */
@Entity
@Table(name = "t_media_airing_music")
@DynamicInsert(true)
@DynamicUpdate(true)
public class AiringMusic extends OnlyIdEntity {

	/** 音源名称 */
	private String musicNname;
	
	/** 音源编号 */
	private String musicCode;
	
	/** 音源时长 */
	private Integer musicTime;
	
	/** 文件大小 */
	private Integer musicFileSize;
	
	/** 文件名 */
	private String musicFileName;
	
	/** 文件保存路径 */
	private String musicFilePath;
	
	/** 创建时间 */
	private Date musicFileCreatetime = new Date();
	
	/** 创建用户 */
	private String musicUser;
	
	/** 文件类型 */
	private String musicType;
	
	
	/**
	 *
	 * getMusicNname
	 * @return String
	 */
	@Column(name = "music_nname")
	public String getMusicNname() {
		return musicNname;
	}
	
	/**
	 *
	 * setMusicNname
	 * @param musicNname String to set
	 */
	public void setMusicNname(String musicNname) {
		this.musicNname = musicNname == null ? "" : musicNname;
	}
	
	/**
	 *
	 * getMusicCode
	 * @return String
	 */
	@Column(name = "music_code")
	public String getMusicCode() {
		return musicCode;
	}
	
	/**
	 *
	 * setMusicCode
	 * @param musicCode String to set
	 */
	public void setMusicCode(String musicCode) {
		this.musicCode = musicCode == null ? "" : musicCode;
	}
	
	/**
	 *
	 * getMusicTime
	 * @return Integer
	 */
	@Column(name = "music_time")
	public Integer getMusicTime() {
		return musicTime;
	}
	
	/**
	 *
	 * setMusicTime
	 * @param musicTime Integer to set
	 */
	public void setMusicTime(Integer musicTime) {
		this.musicTime = musicTime;
	}
	
	/**
	 *
	 * getMusicFileSize
	 * @return Integer
	 */
	@Column(name = "music_file_size")
	public Integer getMusicFileSize() {
		return musicFileSize;
	}
	
	/**
	 *
	 * setMusicFileSize
	 * @param musicFileSize Integer to set
	 */
	public void setMusicFileSize(Integer musicFileSize) {
		this.musicFileSize = musicFileSize;
	}
	
	/**
	 *
	 * getMusicFileName
	 * @return String
	 */
	@Column(name = "music_file_name")
	public String getMusicFileName() {
		return musicFileName;
	}
	
	/**
	 *
	 * setMusicFileName
	 * @param musicFileName String to set
	 */
	public void setMusicFileName(String musicFileName) {
		this.musicFileName = musicFileName == null ? "" : musicFileName;
	}
	
	/**
	 *
	 * getMusicFilePath
	 * @return String
	 */
	@Column(name = "music_file_path")
	public String getMusicFilePath() {
		return musicFilePath;
	}
	
	/**
	 *
	 * setMusicFilePath
	 * @param musicFilePath String to set
	 */
	public void setMusicFilePath(String musicFilePath) {
		this.musicFilePath = musicFilePath == null ? "" : musicFilePath;
	}
	
	/**
	 *
	 * getMusicFileCreatetime
	 * @return Date
	 */
	@Column(name = "music_file_createtime")
	public Date getMusicFileCreatetime() {
		return musicFileCreatetime;
	}
	
	/**
	 *
	 * setMusicFileCreatetime
	 * @param musicFileCreatetime Date to set
	 */
	public void setMusicFileCreatetime(Date musicFileCreatetime) {
		this.musicFileCreatetime = musicFileCreatetime;
	}
	
	/**
	 *
	 * getMusicUser
	 * @return String
	 */
	@Column(name = "music_user")
	public String getMusicUser() {
		return musicUser;
	}
	
	/**
	 *
	 * setMusicUser
	 * @param musicUser String to set
	 */
	public void setMusicUser(String musicUser) {
		this.musicUser = musicUser == null ? "" : musicUser;
	}
	
	/**
	 *
	 * getMusicType
	 * @return String
	 */
	@Column(name = "music_type")
	public String getMusicType() {
		return musicType;
	}
	
	/**
	 *
	 * setMusicType
	 * @param musicType String to set
	 */
	public void setMusicType(String musicType) {
		this.musicType = musicType == null ? "" : musicType;
	}
	
	
	
	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new ReflectionToStringBuilder(
				this,
				ToStringStyle.MULTI_LINE_STYLE)
				.toString();
	}
}
