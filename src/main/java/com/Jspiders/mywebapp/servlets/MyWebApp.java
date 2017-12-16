package com.Jspiders.mywebapp.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class MyWebApp extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String contentType = null;
		/**
		 * getting the request data
		 */
		String address = request.getParameter("address");
		String format = request.getParameter("format");

		/**
		 * @URL
		 */
		String url = "http://localhost:8084/googlemaps/api/geocode";

		if (format.equals("xml")) {
			contentType = MediaType.APPLICATION_XML;
			url = url + "/xml?address=" + address;
		} else if (format.equals("json")) {
			contentType = MediaType.APPLICATION_JSON;
			url = url + "/json?address=" + address;
		} else if (format.equals("text")) {
			contentType = MediaType.TEXT_PLAIN;
			url = url + "/text?address=" + address;
		}

		/**
		 * 1.Build The Client
		 */
		Client client = ClientBuilder.newClient();

		/**
		 * 2.Set The target
		 */
		WebTarget target = client.target(url);

		/**
		 * 3.Get The Response
		 */
		Response respons = target.request(contentType).get();

		/**
		 * 4.Process The Response
		 */
		String resData = (String) respons.readEntity(String.class);

		// StringBuilder builder = new StringBuilder("<html><body>");
		// builder.append(url + " " + "<br>");
		// builder.append("Location: " + address + "<br>");
		// builder.append("format: " + format + "<br>");
		// builder.append("</body></html>");

		/**
		 * printing on the browser
		 */
		response.setContentType(contentType);
		PrintWriter out = response.getWriter();
		out.print(resData);
	}
}
