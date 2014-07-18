package ilg.gnuarmeclipse.packs.cmsis;

import ilg.gnuarmeclipse.packs.Utils;
import ilg.gnuarmeclipse.packs.tree.Leaf;
import ilg.gnuarmeclipse.packs.tree.Node;
import ilg.gnuarmeclipse.packs.tree.Type;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class PdscParserForBuild extends PdscParser {

	public PdscParserForBuild() {
		super();
	}

	// ------------------------------------------------------------------------

	public void parseDevices(Node tree) {

		Element packageElement = m_document.getDocumentElement();
		String firstElementName = packageElement.getNodeName();
		if (!"package".equals(firstElementName)) {
			System.out.println("Missing <packages>, <" + firstElementName
					+ "> encountered");
			return;
		}

		String schemaVersion = packageElement.getAttribute("schemaVersion")
				.trim();

		if (!isSchemaValid(schemaVersion)) {
			return;
		}

		List<Element> childElements = Utils
				.getChildElementsList(packageElement);
		for (Element childElement : childElements) {

			String elementName = childElement.getNodeName();
			if ("devices".equals(elementName)) {
				List<Element> childElements2 = Utils
						.getChildElementsList(childElement);
				for (Element childElement2 : childElements2) {

					String elementName2 = childElement2.getNodeName();
					if ("family".equals(elementName2)) {

						processFamilyElement(childElement2, tree);

					}
				}
			}
		}
	}

	private Node addUniqueVendor(Node parent, String vendorName, String vendorId) {

		if (parent.hasChildren()) {
			for (Leaf child : parent.getChildren()) {
				if (vendorId.equals(child.getProperty(Node.VENDORID_PROPERTY))) {
					return (Node) child;
				}
			}
		}

		Node vendor = Node.addNewChild(parent, Type.VENDOR);
		vendor.setName(vendorName);
		vendor.putProperty(Node.VENDORID_PROPERTY, vendorId);

		return vendor;
	}

	private void processFamilyElement(Element el, Node parent) {

		// Required
		String familyName = el.getAttribute("Dfamily").trim();
		String familyVendor = el.getAttribute("Dvendor").trim();

		String va[] = familyVendor.split("[:]");

		Node vendorNode = addUniqueVendor(parent, va[0], va[1]);

		Node familyNode = Node.addUniqueChild(vendorNode, Type.FAMILY,
				familyName);
		if (!familyNode.hasProperties()) {
			familyNode.putProperty(Node.VENDOR_PROPERTY, va[0]);
			familyNode.putProperty(Node.VENDORID_PROPERTY, va[1]);
		}

		List<Element> childElements = Utils.getChildElementsList(el);
		for (Element childElement : childElements) {

			String elementName = childElement.getNodeName();
			if ("processor".equals(elementName)) {

				processProcessorElement(childElement, familyNode);

			} else if ("subFamily".equals(elementName)) {

				processSubFamilyElement(childElement, familyNode);

			} else if ("device".equals(elementName)) {

				processDeviceElement(childElement, familyNode);

			}
		}
	}

	//
	private void processProcessorElement(Element el, Node parent) {

		String Dcore = el.getAttribute("Dcore").trim();
		String DcoreVersion = el.getAttribute("DcoreVersion").trim();
		String Dfpu = el.getAttribute("Dfpu").trim();
		String Dmpu = el.getAttribute("Dmpu").trim();
		String Dendian = el.getAttribute("Dendian").trim();
		String Dclock = el.getAttribute("Dclock").trim();

		parent.putNonEmptyProperty(Node.CORE_PROPERTY, Dcore);
		parent.putNonEmptyProperty(Node.VERSION_PROPERTY, DcoreVersion);
		parent.putNonEmptyProperty(Node.FPU_PROPERTY, Dfpu);
		parent.putNonEmptyProperty(Node.MPU_PROPERTY, Dmpu);
		parent.putNonEmptyProperty(Node.ENDIAN_PROPERTY, Dendian);
		parent.putNonEmptyProperty(Node.CLOCK_PROPERTY, Dclock);
	}

	private void processSubFamilyElement(Element el, Node parent) {

		String subFamilyName = el.getAttribute("DsubFamily").trim();

		Node subFamilyNode = Node.addUniqueChild(parent, Type.SUBFAMILY,
				subFamilyName);

		List<Element> childElements = Utils.getChildElementsList(el);

		for (Element childElement : childElements) {

			String elementName = childElement.getNodeName();
			if ("device".equals(elementName)) {

				processDeviceElement(childElement, subFamilyNode);

			} else {

				processDevicePropertiesGroup(childElement, subFamilyNode);

			}
		}

		subFamilyNode.setDescription(processDeviceSummary(subFamilyNode));
	}

	private void processDeviceElement(Element el, Node parent) {

		// Required
		String deviceName = el.getAttribute("Dname").trim();

		Node deviceNode = Node.addUniqueChild(parent, Type.DEVICE, deviceName);

		List<Element> childElements = Utils.getChildElementsList(el);

		for (Element childElement : childElements) {

			String elementName = childElement.getNodeName();
			if ("variant".equals(elementName)) {

				// Required
				String variantName = childElement.getAttribute("Dvariant")
						.trim();

				Node variantNode = Node.addUniqueChild(deviceNode,
						Type.VARIANT, variantName);

				List<Element> childElements2 = Utils
						.getChildElementsList(childElement);
				for (Element childElement2 : childElements2) {

					processDevicePropertiesGroup(childElement2, variantNode);
				}

				variantNode.setDescription(processDeviceSummary(variantNode));

			} else {

				processDevicePropertiesGroup(childElement, deviceNode);
			}
		}
		deviceNode.setDescription(processDeviceSummary(deviceNode));
	}

	private void processDevicePropertiesGroup(Element el, Node parent) {

		String elementName = el.getNodeName();
		if ("processor".equals(elementName)) {

			processProcessorElement(el, parent);

		} else if ("memory".equals(elementName)) {

			// Required
			String id = el.getAttribute("id").trim();
			String start = el.getAttribute("start").trim();
			String size = el.getAttribute("size").trim();

			// -
			String Pname = el.getAttribute("Pname").trim();

			// Optional
			String startup = el.getAttribute("startup").trim();
			String init = el.getAttribute("init").trim();
			String defa = el.getAttribute("default").trim();

			Leaf memoryNode = Leaf.addUniqueChild(parent, Type.MEMORY, id);

			memoryNode.putProperty(Node.ID_PROPERTY, id);
			memoryNode.putProperty(Node.START_PROPERTY, start);
			memoryNode.putProperty(Node.SIZE_PROPERTY, size);

			memoryNode.putNonEmptyProperty(Node.PNAME_PROPERTY, Pname);
			memoryNode.putNonEmptyProperty(Node.STARTUP_PROPERTY, startup);
			memoryNode.putNonEmptyProperty(Node.INIT_PROPERTY, init);
			memoryNode.putNonEmptyProperty(Node.DEFAULT_PROPERTY, defa);
		}
	}

	// ------------------------------------------------------------------------

	public void parseBoards(Node tree) {

		Element packageElement = m_document.getDocumentElement();
		String firstElementName = packageElement.getNodeName();
		if (!"package".equals(firstElementName)) {
			System.out.println("Missing <packages>, <" + firstElementName
					+ "> encountered");
			return;
		}

		String schemaVersion = packageElement.getAttribute("schemaVersion")
				.trim();

		if (!isSchemaValid(schemaVersion)) {
			return;
		}

		List<Element> childElements = Utils
				.getChildElementsList(packageElement);
		for (Element childElement : childElements) {

			String elementName = childElement.getNodeName();
			if ("boards".equals(elementName)) {
				List<Element> childElements2 = Utils
						.getChildElementsList(childElement);
				for (Element childElement2 : childElements2) {

					String elementName2 = childElement2.getNodeName();
					if ("board".equals(elementName2)) {

						processBoardElement(childElement2, tree);

					}
				}
			}
		}
	}

	private void processBoardElement(Element el, Node parent) {

		// Required
		String boardVendor = el.getAttribute("vendor").trim();
		String boardName = el.getAttribute("name").trim();

		// Optional
		String boardRevision = el.getAttribute("revision").trim();

		Node vendorNode = Node.addUniqueChild(parent, Type.VENDOR, boardVendor);

		String name = boardName;
		if (boardRevision.length() > 0) {
			name += " (" + boardRevision + ")";
		}

		Node boardNode = Node.addUniqueChild(vendorNode, Type.BOARD, name);

		List<Element> childElements = Utils.getChildElementsList(el);

		for (Element childElement : childElements) {

			String elementName = childElement.getNodeName();

			if ("mountedDevice".equals(elementName)) {

				// Required
				String Dvendor = childElement.getAttribute("Dvendor").trim();

				// Optional
				String Dname = childElement.getAttribute("Dname").trim();

				if (Dname.length() > 0) {
					Node deviceNode = Node.addNewChild(boardNode, Type.DEVICE);
					deviceNode.setName(Dname);

					String va[] = Dvendor.split(":");
					deviceNode.putProperty(Node.VENDOR_PROPERTY, va[0]);
					deviceNode.putProperty(Node.VENDORID_PROPERTY, va[1]);
				}
			} else if ("feature".equals(elementName)) {

				// <xs:element name="feature" type="BoardFeatureType"
				// maxOccurs="unbounded"></xs:element>

				processFeatureElement(childElement, boardNode);
			}
		}

		String clock = boardNode.getProperty(Node.CLOCK_PROPERTY, "");
		if (clock.length() > 0) {
			String summary = "";
			try {
				int clockMHz = Integer.parseInt(clock) / 1000000;
				if (summary.length() > 0) {
					summary += ", ";
				}
				summary += String.valueOf(clockMHz) + " MHz";
			} catch (NumberFormatException e) {
				// Ignore not number
			}
			boardNode.setDescription(summary);
		}
	}

	private void processFeatureElement(Element el, Node parent) {

		// Required
		String featureType = el.getAttribute("type").trim();

		// Optional
		String featureN = el.getAttribute("n").trim();

		// String featureM = el.getAttribute("m").trim();
		// String name = el.getAttribute("name").trim();
		// String Pname = el.getAttribute("Pname").trim();

		if ("XTAL".equals(featureType)) {
			parent.putNonEmptyProperty(Node.CLOCK_PROPERTY, featureN);
		}
	}

	// ------------------------------------------------------------------------
}