package info.hexin.jmacs.mvc.handler.upload;

import info.hexin.lang.Exceptions;

import java.io.UnsupportedEncodingException;

/**
 * 内存格式
 * 
 * @author hexin
 * 
 */
public class MemTempFile extends TempFile {

    byte[] buffer;

    public void append(byte[] buffer, int s, int len) {
        this.buffer = buffer;
    }

    @Override
    public String getString() {
        return new String(buffer);
    }

    @Override
    public String getString(String charset) {
        try {
            return new String(buffer, charset);
        } catch (UnsupportedEncodingException e) {
            throw Exceptions.make(e);
        }
    }
}
