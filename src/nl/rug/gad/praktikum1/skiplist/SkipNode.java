package nl.rug.gad.praktikum1.skiplist;

public class SkipNode {

	private SkipNode before, after, above, below;
	private String text;
	
	private boolean isFirst = false, isLast = false;
	
	public static final SkipNode NullNode = new SkipNode("");
	
	public SkipNode(String text){
		setText(text);
		setBefore(NullNode);
		setAfter(NullNode);
		setAbove(NullNode);
		setBelow(NullNode);
	}
	
	public int compareTo(String text){
		if(isFirst) return Integer.MIN_VALUE;
		if(isLast) return Integer.MAX_VALUE;
		return getText().compareTo(text);
	}
	
	public void setText(String text){
		this.text = text;
	}
	
	public String getText(){
		return text;
	}
	
	public void setIsFirst(){
		isFirst = true;
		text = "first";
	}
	
	public boolean isFirst(){
		return isFirst;
	}
	
	public void setIsLast(){
		isLast = true;
		text = "last";
	}
	
	public boolean isLast(){
		return isLast;
	}
	
	public void setAfter(SkipNode node){
		after = node;
	}
	
	public SkipNode after(){
		return after;
	}
	
	public void setBefore(SkipNode node){
		before = node;
	}
	
	public SkipNode before(){
		return before;
	}
	
	public void setBelow(SkipNode node){
		below = node;
	}
	
	public SkipNode below(){
		return below;
	}
	
	public void setAbove(SkipNode node){
		above = node;
	}
	
	public SkipNode above(){
		return above;
	}
}
