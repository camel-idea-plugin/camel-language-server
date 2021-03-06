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
package com.github.cameltooling.lsp.internal.completion.camelapplicationproperties;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionList;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.TextDocumentItem;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.junit.jupiter.api.Test;

import com.github.cameltooling.lsp.internal.AbstractCamelLanguageServerTest;
import com.github.cameltooling.lsp.internal.CamelLanguageServer;

class CamelPropertiesComponentOptionEnumerationValuesCompletionTest extends AbstractCamelLanguageServerTest {

	@Test
	void testProvideCompletion() throws Exception {
		CompletableFuture<Either<List<CompletionItem>, CompletionList>> completions = retrieveCompletion(new Position(0, 52), "camel.component.acomponent.errorHandlerLoggingLevel=T");
		
		assertThat(completions.get().getLeft()).hasSize(6);
	}
	
	@Test
	void testProvideCompletionIsFilteredWhenInsideValue() throws Exception {
		CompletableFuture<Either<List<CompletionItem>, CompletionList>> completions = retrieveCompletion(new Position(0, 53), "camel.component.acomponent.errorHandlerLoggingLevel=T");
		
		CompletionItem expectedCompletionItem = new CompletionItem("TRACE");
		expectedCompletionItem.setTextEdit(Either.forLeft(new TextEdit(new Range(new Position(0, 52), new Position(0, 53)), "TRACE")));
		
		assertThat(completions.get().getLeft()).containsOnly(expectedCompletionItem);
	}
	
	@Test
	void testProvideNoCompletionOnInvalidKeys() throws Exception {
		CompletableFuture<Either<List<CompletionItem>, CompletionList>> completions = retrieveCompletion(new Position(0, 27), "camel.component.acomponent=");
		
		assertThat(completions.get().getLeft()).isEmpty();
	}
		
	protected CompletableFuture<Either<List<CompletionItem>, CompletionList>> retrieveCompletion(Position position, String text) throws URISyntaxException, InterruptedException, ExecutionException {
		String fileName = "a.properties";
		CamelLanguageServer camelLanguageServer = initializeLanguageServer(fileName, new TextDocumentItem(fileName, CamelLanguageServer.LANGUAGE_ID, 0, text));
		return getCompletionFor(camelLanguageServer, position, fileName);
	}
	
	@Override
	protected Map<Object, Object> getInitializationOptions() {
		String component = "{\n" + 
				" \"component\": {\n" + 
				"    \"kind\": \"component\",\n" + 
				"    \"scheme\": \"acomponent\",\n" + 
				"    \"syntax\": \"acomponent:withsyntax\",\n" + 
				"    \"title\": \"A Component\",\n" + 
				"    \"description\": \"Description of my component.\",\n" + 
				"    \"label\": \"\",\n" + 
				"    \"deprecated\": true,\n" + 
				"    \"deprecationNote\": \"\",\n" + 
				"    \"async\": false,\n" + 
				"    \"consumerOnly\": true,\n" + 
				"    \"producerOnly\": false,\n" + 
				"    \"lenientProperties\": false,\n" + 
				"    \"javaType\": \"org.test.AComponent\",\n" + 
				"    \"firstVersion\": \"1.0.0\",\n" + 
				"    \"groupId\": \"org.test\",\n" + 
				"    \"artifactId\": \"camel-acomponent\",\n" + 
				"    \"version\": \"3.0.0\"\n" + 
				"  },\n" + 
				"  \"componentProperties\": {\n" + 
				"\"errorHandlerLoggingLevel\": {\"kind\":\"property\",\"displayName\":\"Error Handler Logging Level\",\"group\":\"logging\",\"label\":\"consumer,logging\",\"required\":false,\"type\":\"object\",\"javaType\":\"org.apache.camel.LoggingLevel\",\"enum\":[\"TRACE\",\"DEBUG\",\"INFO\",\"WARN\",\"ERROR\",\"OFF\"],\"deprecated\":false,\"secret\":false,\"defaultValue\":\"WARN\",\"description\":\"A component property description.\"}" + 
				"  },\n" + 
				"  \"properties\": {\n" +
				"  }\n" + 
				"}";
		return createMapSettingsWithComponent(component);
	}
}
