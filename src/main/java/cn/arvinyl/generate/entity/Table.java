package cn.arvinyl.generate.entity;

import java.util.List;

/**
 * 表实体
 */
public class Table {
	
	private String name;//表名称
	private String name2;//处理后的表名称,对应的类名
	private String comment;//介绍
	private String key;// 主键列
	private List<Column> columns;//列集合

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName2() {
		return name2;
	}

	public void setName2(String name2) {
		this.name2 = name2;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

	@Override
	public String toString() {
		return "Table{" +
				"name='" + name + '\'' +
				", name2='" + name2 + '\'' +
				", comment='" + comment + '\'' +
				", key='" + key + '\'' +
				", columns=" + columns +
				'}';
	}
}
