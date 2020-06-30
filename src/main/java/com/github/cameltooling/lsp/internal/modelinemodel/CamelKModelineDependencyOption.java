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

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.apache.camel.catalog.CamelCatalog;
import org.eclipse.lsp4j.CompletionItem;

import com.github.cameltooling.lsp.internal.completion.CompletionResolverUtils;
import com.github.cameltooling.model.util.ModelHelper;

public class CamelKModelineDependencyOption implements ICamelKModelineOptionValue {

	private String value;
	private int startPosition;

	public CamelKModelineDependencyOption(String value, int startPosition) {
		this.value = value;
		this.startPosition = startPosition;
	}

	@Override
	public int getStartPositionInLine() {
		return startPosition;
	}

	@Override
	public int getEndPositionInLine() {
		return startPosition + (value != null? value.length() : 0);
	}

	@Override
	public String getValueAsString() {
		return value;
	}
	
	@Override
	public CompletableFuture<List<CompletionItem>> getCompletions(int position, CompletableFuture<CamelCatalog> camelCatalog) {
		if(getStartPositionInLine() == position) {
			return camelCatalog.thenApply(catalog -> catalog.findComponentNames().stream()
				.map(componentName -> ModelHelper.generateComponentModel(catalog.componentJSonSchema(componentName), true))
				.map(componentModel -> {
					CompletionItem completionItem = new CompletionItem(componentModel.getArtifactId());
					completionItem.setDocumentation(componentModel.getDescription());
					completionItem.setDeprecated(Boolean.valueOf(componentModel.getDeprecated()));
					CompletionResolverUtils.applyTextEditToCompletionItem(this, completionItem);
					return completionItem;
				})
				.collect(Collectors.toList()));
		}
		return ICamelKModelineOptionValue.super.getCompletions(position, camelCatalog);
	}

}
