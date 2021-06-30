/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.cameltooling.lsp.internal.modelinemodel;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.apache.camel.catalog.CamelCatalog;
import org.eclipse.lsp4j.CompletionItem;

public class CamelKModelineConfigOption implements ICamelKModelineOptionValue {

	private String value;
	private int startPosition;
	private int line;

	public CamelKModelineConfigOption(String value, int startPosition, int line) {
		this.value = value;
		this.startPosition = startPosition;
		this.line = line;
	}

	@Override
	public int getLine() {
		return line;
	}

	@Override
	public int getStartPositionInLine() {
		return startPosition;
	}

	@Override
	public int getEndPositionInLine() {
		return startPosition + value.length();
	}

	@Override
	public String getValueAsString() {
		return value;
	}
	
	@Override
	public CompletableFuture<List<CompletionItem>> getCompletions(int position, CompletableFuture<CamelCatalog> camelCatalog) {
		if(position == startPosition) {
			CompletionItem configmap = new CompletionItem("configmap:");
			configmap.setDocumentation("Add a runtime configuration from a Configmap (syntax: configmap:name[/key], "
					+ "where name represents the configmap name, "
					+ "key optionally represents the configmap key to be filtered)");
			CompletionItem secret = new CompletionItem("secret:");
			secret.setDocumentation("Add a runtime configuration from a Secret (syntax: secret:name[/key], "
					+ "where name represents the secret name, "
					+ "key optionally represents the secret key to be filtered)");
			CompletionItem file = new CompletionItem("file:");
			file.setDocumentation("Add a runtime configuration from a file (syntax: file:name, "
					+ "where name represents the local file path)");
			return CompletableFuture.completedFuture(Arrays.asList(configmap, secret, file));
		}
		return ICamelKModelineOptionValue.super.getCompletions(position, camelCatalog);
	}

}
