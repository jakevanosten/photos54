package model;

public class Tag {

	String type;
	String content;
	
	public Tag(String type, String content) {
		this.type = type;
		this.content = content;
	}
	
	public String getType() {
		return this.type;
	}
	
	public String getContent() {
		return this.content;
	}
	
	public boolean equals(Object object) {
		boolean result = false;
		if (object == null || !(object instanceof Tag)) {
				result = false;
		}else {
				Tag t = (Tag) object;
				if ((this.getType().toLowerCase().equals(t.getType().toLowerCase())) || (this.getContent().toLowerCase().equals(t.getContent().toLowerCase()))) {
					result = true;
				}
		}
		return result;
	}	
	
}
