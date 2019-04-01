package com.accenture.bars.microservices.client.renzchler.s.oxino.file;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.jboss.logging.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.accenture.bars.microservices.client.renzchler.s.oxino.domain.Record;

public class BarsXMLUtils {

	public static boolean generateXML(List<Record> records) {
		int id = 0;
		LocalDateTime time = LocalDateTime.now();
		String timestamp = time.getMonthValue() + "" + time.getDayOfMonth()
				+ "" + time.getYear() + "_" + time.getHour() + ""
				+ time.getMinute() + "" + time.getSecond();
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("BARS");
			doc.appendChild(rootElement);
			for (Record record : records) {
				// request elements
				Element request = doc.createElement("request");
				rootElement.appendChild(request);

				Attr attr = doc.createAttribute("billing-id");
				attr.setValue(Integer.toString((id + 1)));
				request.setAttributeNode(attr);

				Element billingCycle = doc.createElement("billing-cycle");
				billingCycle.appendChild(doc.createTextNode(Integer
						.toString(record.getBillingCycle())));
				request.appendChild(billingCycle);

				Element startDate = doc.createElement("start-date");
				startDate.appendChild(doc.createTextNode(record.getStartDate()
						.toString()));
				request.appendChild(startDate);

				Element endDate = doc.createElement("end-date");
				endDate.appendChild(doc.createTextNode(record.getEndDate()
						.toString()));
				request.appendChild(endDate);

				Element amount = doc.createElement("amount");
				amount.appendChild(doc.createTextNode(Double.toString(record
						.getAmount())));
				request.appendChild(amount);

				Element firstName = doc.createElement("first-name");
				firstName.appendChild(doc.createTextNode(record
						.getCustomerFirstName()));
				request.appendChild(firstName);

				Element lastName = doc.createElement("last-name");
				lastName.appendChild(doc.createTextNode(record
						.getCustomerLastName()));
				request.appendChild(lastName);

				TransformerFactory transformerFactory = TransformerFactory
						.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new File(
						"C:\\BARS\\Report\\Bars_Report-" + timestamp + ".xml"));

				transformer.transform(source, result);
				id++;
			}

			return true;

		} catch (ParserConfigurationException e) {
			Logger.getLogger(BarsXMLUtils.class).trace(e);
		} catch (TransformerException m) {
			Logger.getLogger(BarsXMLUtils.class).trace(m);
		}

		return false;

	}

}
