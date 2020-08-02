package cn.tedu.pojo;

import java.io.Serializable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class BaseObject implements Serializable {
	private static final long serialVersionUID = -1556919362060824963L;

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public String toString() {
		return JSONObject.toJSONStringWithDateFormat(this, JSON.DEFFAULT_DATE_FORMAT,SerializerFeature.WriteDateUseDateFormat);
	}

}
