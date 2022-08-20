package com.smk.blog.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.smk.blog.services.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		// TODO Auto-generated method stub

		// File name
		String fileName = file.getOriginalFilename();
		String randomString = UUID.randomUUID().toString();
		String randomFileName = randomString.concat(fileName.substring(fileName.lastIndexOf(".")));
		// full path
		String fullPath = path + File.separator + randomFileName;
		// created folder if not created
		File folder = new File(path);
		if (!folder.exists()) {
			folder.mkdir();
		}
		// copy file
		Files.copy(file.getInputStream(), Paths.get(fullPath));
		return randomFileName;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		String fullPath = path + File.separator + fileName;
		InputStream is = new FileInputStream(fullPath);
		return is;
	}

}
