package com;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.exception.MatrixException;

public interface Read {
	List<AccessControl> processFile(InputStream in) throws MatrixException;
	Map<String,String> processFileMap(InputStream in) throws MatrixException;
}
