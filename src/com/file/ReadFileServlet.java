package com.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.AccessControl;
import com.ReadMatrix;
import com.ReadQuery;
import com.SearchPrivileges;
import com.exception.MatrixException;
import com.google.gson.Gson;
import com.packtpub.wflydevelopment.chapter7.entity.Seat;

@WebServlet(urlPatterns = "/readFile")
@SessionScoped
@Named
public class ReadFileServlet extends HttpServlet {
	private String firstNameFile = "";

	private static final long serialVersionUID = 1L;
	@Inject
	private ReadMatrix readMatrix;
	@Inject
	private ReadQuery readQuery;
	private Map<String, String> subjects;
	private Map<String, String> querys;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (ServletFileUpload.isMultipartContent(req)) {
			try {
				DiskFileItemFactory factory = new DiskFileItemFactory();
				ServletContext servletContext = this.getServletConfig().getServletContext();
				File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");

				factory.setRepository(repository);
				ServletFileUpload fileUpload = new ServletFileUpload(factory);
				List<FileItem> files = fileUpload.parseRequest(req);

				if (files != null && !files.isEmpty()) {
					for (FileItem item : files) {

						InputStream in = item.getInputStream();
						try {
							if ("".equalsIgnoreCase(firstNameFile)) {
								subjects = readMatrix.processFileMap(in);
//								for (Map.Entry<String, String> entry : subjects.entrySet()) {
//									System.out
//											.println("entry.getKey()=" + entry.getKey() + ",Value=" + entry.getValue());
//								}
								firstNameFile = item.getName();
							} else {
								System.out.println("Fashion-------------");
								querys = readQuery.processFileMap(in);
								SearchPrivileges searchPrivileges = new SearchPrivileges(subjects,querys);
								 List<AccessControl>  lista =searchPrivileges.search();
								 for (AccessControl ac:lista){
									 System.out.println(ac.getInfo());
								 }
								
//								for (Map.Entry<String, String> entry : querys.entrySet()) {
//									System.out
//											.println("entry.getKey()=" + entry.getKey() + ",Value=" + entry.getValue());
//								}
							}

						} catch (MatrixException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}
			} catch (FileUploadException e) {
				e.printStackTrace();
			}

		}

		Seat seat = new Seat(7, "Saymon", 1800);
		String json = new Gson().toJson(seat);
		resp.setContentType("application/json");
		resp.getWriter().write(json);

	}
}