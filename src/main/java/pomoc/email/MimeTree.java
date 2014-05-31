package pomoc.email;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.james.mime4j.dom.BinaryBody;
import org.apache.james.mime4j.dom.Body;
import org.apache.james.mime4j.dom.Entity;
import org.apache.james.mime4j.dom.Header;
import org.apache.james.mime4j.dom.Message;
import org.apache.james.mime4j.dom.Multipart;
import org.apache.james.mime4j.dom.TextBody;
import org.apache.james.mime4j.dom.address.MailboxList;
import org.apache.james.mime4j.dom.field.ContentTypeField;
import org.apache.james.mime4j.dom.field.DateTimeField;
import org.apache.james.mime4j.dom.field.MailboxListField;
import org.apache.james.mime4j.message.BodyPart;
import org.apache.james.mime4j.message.MessageImpl;
import org.apache.james.mime4j.stream.Field;

public class MimeTree {

	public static class Node {
		public Node(String type, Object data) {
			this.type = type;
			this.data = data;
			this.children = new ArrayList<Node>();
			this.parent = null;
		}

		public void add(Node child) {
			child.setParent(this);
			children.add(child);
		}

		private void setParent(Node node) {
			this.parent = node;
		}

		public Node parent() {
			return parent;
		}
		
		public Object getData() {
			return data;
		}
		
		public String getType() {
			return type;
		}
		
		public List<Node> getChildren() {
			return children;
		}
		private final String type;
		private final Object data;
		private List<Node> children;
		private Node parent;
	};

	public enum NodeType {
		MESSAGE("Message"), BODY_PART("Body part"), MULTIPART("Multipart"), PREAMBLE(
				"Preamble"), EPILOGUE("Epilogue"), HEADER("Header"), TEXT_BODY(
				"Text body"), BINARY_BODY("Binary body"), CONTENT_TYPE("Content-Type"), 
				DATE("Date"), FROM("From"), SUBJECT("Subject");

		private final String typeString;

		private NodeType(String typeString) {
			this.typeString = typeString;
		}

		public boolean is(String type) {
			return typeString.equals(type);
		}

		public String typeString() {
			return typeString;
		}

	};

	Node root;

	public MimeTree(Message message) {
		root = createNode(message);
	}

	public Date getDate() {
		Node header = getMainHeaderNode(root);
		if (header != null) {
			return getHeaderValue(header, NodeType.DATE);
		}
		return null;
	}
	
	public String getSubject() {
		Node header = getMainHeaderNode(root);
		if (header != null) {
			return getHeaderValue(header, NodeType.SUBJECT);
		}
		return null;
	}
	
	private <T> T getHeaderValue(Node node, NodeType type) {
		for (Node child: node.getChildren()) {
			if (type.is(child.getType())) {
				Object data = child.getData();
				if (data instanceof DateTimeField) {
					return (T) ((DateTimeField) data).getDate();
				}
				if (data instanceof ContentTypeField) {
					return (T) ((ContentTypeField) data).getMimeType();
				}
				if (data instanceof MailboxListField) {
					MailboxListField field = (MailboxListField) data;
	                MailboxList list = field.getMailboxList();
	                if (!list.isEmpty()) {
	                	return (T) list.get(0).getAddress();
	                }
	                return null;
				}
				if (data instanceof Field) {
					return (T) ((Field) data).getBody();
				}
			}
		}
		return null;
	}

	private Node getMainHeaderNode(Node node) {
		if (NodeType.HEADER.is(node.getType())) {
			return node;
		}
		for (Node child: node.getChildren()) {
			Node result = getMainHeaderNode(child);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	public String getFrom() {
		Node header = getMainHeaderNode(root);
		if (header != null) {
			return getHeaderValue(header, NodeType.FROM);
		}
		return null;
	}
	
	public String getPlainMessage() {
		Node header = getPlainTextHeaderNode(root);
		if (header != null) {
			Node headerParent = header.parent();
			for (Node child: headerParent.getChildren()) {
				if (NodeType.TEXT_BODY.is(child.getType())) {
					TextBody body = (TextBody) child.getData();
					StringBuilder sb = new StringBuilder();
					try {
						Reader r = body.getReader();
						int c;
						while ((c = r.read()) != -1) {
							sb.append((char) c);
						}
					} catch (IOException ex) {
						ex.printStackTrace();
					}
					return sb.toString();
				}
			}
		}
		return null;	
	}
	
	private Node getPlainTextHeaderNode(Node node) {
		if (NodeType.HEADER.is(node.getType())) {
			if ("text/plain".equalsIgnoreCase(getContentType(node))) {
				return node;
			}
		}
		for (Node child: node.getChildren()) {
			Node result = getPlainTextHeaderNode(child);
			if (result != null) {
				return result;
			}
		}
		return null;
		
	}

	private String getContentType(Node node) {
		return getHeaderValue(node, NodeType.CONTENT_TYPE);
	}

	private Node createNode(Entity entity) {
		Node node;
		if (entity instanceof BodyPart) {
			node = new Node(NodeType.BODY_PART.typeString(), entity);
		} else {
			node = new Node(NodeType.MESSAGE.typeString(), entity);
		}
		node.add(createNode(entity.getHeader()));
		Body body = entity.getBody();

		if (body instanceof Multipart) {
			node.add(createNode((Multipart) body));
		} else if (body instanceof MessageImpl) {
			node.add(createNode((MessageImpl) body));

		} else {
			String type = NodeType.TEXT_BODY.typeString();
			if (body instanceof BinaryBody) {
				type = NodeType.BINARY_BODY.typeString();
			}

			//type += " (" + entity.getMimeType() + ")";
			node.add(new Node(type, body));

		}

		return node;
	}

	private Node createNode(Multipart body) {
		Node node = new Node(NodeType.MULTIPART.typeString(),
				body);

		node.add(new Node(NodeType.PREAMBLE.typeString(), body.getPreamble()));
		for (Entity part : body.getBodyParts()) {
			node.add(createNode(part));
		}
		node.add(new Node(NodeType.EPILOGUE.typeString(), body.getEpilogue()));

		return node;
	}

	private Node createNode(Header header) {
		Node node = new Node(NodeType.HEADER.typeString(),
				header);

		for (Field field : header.getFields()) {
			String name = field.getName();
			node.add(new Node(name, field));
		}

		return node;
	}
}
