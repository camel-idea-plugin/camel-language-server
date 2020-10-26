/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.cameltooling.lsp.internal.catalog.model;

import java.util.List;

public class ComponentOptionModel {

	private BaseOptionModel data = new BaseOptionModel();
	private Object defaultValue;
	private List<String> enums;

	public String getName() {
		return data.getName();
	}

	public void setName(String name) {
		this.data.setName(name);
	}

	public String getKind() {
		return data.getKind();
	}

	public void setKind(String kind) {
		this.data.setKind(kind);
	}

	public String getGroup() {
		return data.getGroup();
	}

	public void setGroup(String group) {
		this.data.setGroup(group);
	}

	public boolean getRequired() {
		return data.isRequired();
	}

	public void setRequired(boolean required) {
		this.data.setRequired(required);
	}

	public String getType() {
		return data.getType();
	}

	public void setType(String type) {
		this.data.setType(type);
	}

	public String getJavaType() {
		return data.getJavaType();
	}

	public void setJavaType(String javaType) {
		this.data.setJavaType(javaType);
	}

	public boolean getDeprecated() {
		return data.isDeprecated();
	}

	public void setDeprecated(boolean deprecated) {
		this.data.setDeprecated(deprecated);
	}

	public boolean getSecret() {
		return data.isSecret();
	}

	public void setSecret(boolean secret) {
		this.data.setSecret(secret);
	}

	public String getDescription() {
		return data.getDescription();
	}

	public void setDescription(String description) {
		this.data.setDescription(description);
	}

	public Object getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
	}

	public List<String> getEnums() {
		return enums;
	}

	public void setEnums(List<String> enums) {
		this.enums = enums;
	}

}
