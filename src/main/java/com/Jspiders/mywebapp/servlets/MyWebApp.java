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
		 * @Invoking the Google Maps Web Services
		 */

		/**
		 * 1.Build The Client if @Http protocol is used we can go for this it is
		 * not storing any key store or store since it is http
		 */
		Client client = ClientBuilder.newClient();
		System.out.println(client.getClass().getName());

		/**
		 * if @Https protocol is used we can go for this Here @builder DESIGN
		 * PATTERN and it is a part of creational group
		 * 
		 * design patterns are divide as groups creational behavioral
		 * observational strategy
		 */
		// ClientBuilder builder = ClientBuilder.newBuilder();
		// Client client = builder.build();

		/**
		 * 2.Set The target
		 */
		WebTarget target = client.target(url);

		/**
		 * 3.Get The Response
		 */
		Response respons = target
							.request()
							.accept(contentType)
							.header("myName", "Pradeep")
							.cookie("id", "1234")
							.get();

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
