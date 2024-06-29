package com.jqdi.easylogin.core.qq.request.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class UserInfoResp {
	/**
	 * 错误码，0代表成功（错误码：https://wiki.connect.qq.com/%e5%85%ac%e5%85%b1%e8%bf%94%e5%9b%9e%e7%a0%81%e8%af%b4%e6%98%8e）
	 */
	@JsonProperty("ret")
	private Integer ret;
	/**
	 * 如果ret<0，会有相应的错误信息提示，返回数据全部用UTF-8编码
	 */
	@JsonProperty("msg")
	private String msg;
	
	/**
	 * 用户在QQ空间的昵称。
	 */
	@JsonProperty("nickname")
	private String nickname;
	/**
	 * 大小为30×30像素的QQ空间头像URL。
	 */
	@JsonProperty("figureurl")
	private String figureurl;
	/**
	 * 大小为50×50像素的QQ空间头像URL。
	 */
	@JsonProperty("figureurl_1")
	private String figureurl1;
	/**
	 * 大小为100×100像素的QQ空间头像URL。
	 */
	@JsonProperty("figureurl_2")
	private String figureurl2;
	/**
	 * 大小为40×40像素的QQ头像URL。
	 */
	@JsonProperty("figureurl_qq_1")
	private String figureurlQq1;
	/**
	 * 大小为100×100像素的QQ头像URL。需要注意，不是所有的用户都拥有QQ的100x100的头像，但40x40像素则是一定会有。
	 */
	@JsonProperty("figureurl_qq_2")
	private String figureurlQq2;
	/**
	 * 性别。 如果获取不到则默认返回"男"
	 */
	@JsonProperty("gender")
	private String gender;
	/**
	 * 性别类型。默认返回2
	 */
	@JsonProperty("gender_type")
	private String genderType;
	/**
	 * 省
	 */
	@JsonProperty("province")
	private String province;
	/**
	 * 市
	 */
	@JsonProperty("city")
	private String city;
	/**
	 * 年
	 */
	@JsonProperty("year")
	private String year;
	/**
	 * 星座
	 */
	@JsonProperty("constellation")
	private String constellation;
	/**
	 * 标识用户是否为黄钻用户
	 */
	@JsonProperty("is_yellow_vip")
	private String isYellowVip;
	/**
	 * 黄钻等级
	 */
	@JsonProperty("yellowVipLevel")
	private String yellowVipLevel;
	/**
	 * 是否为年费黄钻用户
	 */
	@JsonProperty("is_yellow_year_vip")
	private String isYellowYearVip;
}
