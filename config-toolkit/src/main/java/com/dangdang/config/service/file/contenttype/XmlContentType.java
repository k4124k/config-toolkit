package com.dangdang.config.service.file.contenttype;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import com.dangdang.config.service.exception.InvalidPathException;
import com.google.common.collect.Maps;
import org.apache.commons.io.IOUtils;

/**
 * <p>
 * The XML document must have the following DOCTYPE declaration:
 * 
 * <pre>
 * &lt;!DOCTYPE properties SYSTEM "http://java.sun.com/dtd/properties.dtd"&gt;
 * </pre>
 * 
 * Furthermore, the document must satisfy the properties DTD described above.
 * 
 * @author <a href="mailto:wangyuxuan@dangdang.com">Yuxuan Wang</a>
 *
 */
public class XmlContentType implements ContentType {

	@Override
	public Map<String, String> resolve(byte[] data, String encoding) throws InvalidPathException {
		Properties props = new Properties();
		// jdk6
		InputStream in = null;
		try {
			in = new ByteArrayInputStream(data);
			props.loadFromXML(in);
		} catch (IOException e){
			throw new InvalidPathException(e);
		} finally{
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		/*try (InputStream in = new ByteArrayInputStream(data)) {
			props.loadFromXML(in);
		} catch (IOException e) {
			throw new InvalidPathException(e);
		}*/

		return Maps.fromProperties(props);
	}

}
