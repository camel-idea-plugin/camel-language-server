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
package org.apache.camel.tools.lsp.internal.hover;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.CompletableFuture;

import org.apache.camel.tools.lsp.internal.AbstractCamelLanguageServerTest;
import org.apache.camel.tools.lsp.internal.CamelLanguageServer;
import org.eclipse.lsp4j.Hover;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.TextDocumentPositionParams;
import org.junit.Test;

public class CamelLanguageServerHoverTest extends AbstractCamelLanguageServerTest {
	
	@Test
	public void testProvideDocumentationOnHover() throws Exception {
		CamelLanguageServer camelLanguageServer = initializeLanguageServer("<from uri=\"ahc:httpUri\" xmlns=\"http://camel.apache.org/schema/spring\"></from>\n");
		
		TextDocumentPositionParams position = new TextDocumentPositionParams(new TextDocumentIdentifier(DUMMY_URI), new Position(0, 13));
		CompletableFuture<Hover> hover = camelLanguageServer.getTextDocumentService().hover(position);
		
		assertThat(hover.get().getContents().get(0).getLeft()).isEqualTo(AHC_DOCUMENTATION);
	}
	
	@Test
	public void testDontProvideDocumentationOnHoverForBadPlaces() throws Exception {
		CamelLanguageServer camelLanguageServer = initializeLanguageServer("<from uri=\"ahc:httpUri\" xmlns=\"http://camel.apache.org/schema/spring\"></from>\n");
		
		TextDocumentPositionParams position = new TextDocumentPositionParams(new TextDocumentIdentifier(DUMMY_URI), new Position(0, 4));
		CompletableFuture<Hover> hover = camelLanguageServer.getTextDocumentService().hover(position);
		
		assertThat(hover.get().getContents()).isEmpty();
	}
	
	@Test
	public void testDontProvideDocumentationOnHoverWhenEndingWithAnd() throws Exception {
		CamelLanguageServer camelLanguageServer = initializeLanguageServer("<from uri=\"ahc:httpUri?test=test&\" xmlns=\"http://camel.apache.org/schema/spring\"></from>\n");
		
		TextDocumentPositionParams position = new TextDocumentPositionParams(new TextDocumentIdentifier(DUMMY_URI), new Position(0, 15));
		CompletableFuture<Hover> hover = camelLanguageServer.getTextDocumentService().hover(position);
		
		assertThat(hover.get().getContents()).isEmpty();
	}

}
