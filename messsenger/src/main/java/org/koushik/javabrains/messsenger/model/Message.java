package org.koushik.javabrains.messsenger.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement // we are using this annotation so that we can send value of this object to client in xml or json form.
public class Message {
	private long id;
	private String message;
	private Date created;
	private String author;
	private Map<Long,Comment> comments=new HashMap<Long, Comment>();
	private List<Link> links=new ArrayList<Link>();
	
	public Message() {
	}
	public Message(long id, String message,String author) {
		super();
		this.id = id;
		this.message = message;
		this.created = new Date();
		this.author = author;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
	@XmlTransient//we are making it transient because we dont want togett all comments with message json hence declaring itnas transient
	public Map<Long, Comment> getComments() {
		return comments;
	}
	public void setComments(Map<Long, Comment> comments) {
		this.comments = comments;
	}
	public List<Link> getLinks() {
		return links;
	}
	public void setLinks(List<Link> links) {
		this.links = links;
	}
	public void addLink(String url,String rel) {
		Link link=new Link();
		link.setLink(url);
		link.setRel(rel);
		links.add(link);
		
	}
	@Override
	public String toString() {
		return "Message [id=" + id + ", message=" + message + ", created=" + created + ", author=" + author + "]";
	}

}
