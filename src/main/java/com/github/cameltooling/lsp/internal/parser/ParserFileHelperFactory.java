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
package com.github.cameltooling.lsp.internal.parser;

import org.eclipse.lsp4j.TextDocumentItem;

public class ParserFileHelperFactory {
	
	private static final String CAMELK_GROOVY_FILENAME_SUFFIX = ".camelk.groovy";
	private static final String SHEBANG_CAMEL_K = "#!/usr/bin/env camel-k";

	public ParserFileHelper getCorrespondingParserFileHelper(TextDocumentItem textDocumentItem, int line) {
		ParserXMLFileHelper xmlParser = new ParserXMLFileHelper();
		String uri = textDocumentItem.getUri();
		if (uri.endsWith(".xml") && xmlParser.getCorrespondingCamelNodeForCompletion(textDocumentItem, line) != null) {
			return xmlParser;
		} else if(isCamelJavaDSL(textDocumentItem, uri)) {
			ParserJavaFileHelper javaParser = new ParserJavaFileHelper();
			if (javaParser.getCorrespondingMethodName(textDocumentItem, line) != null) {
				return javaParser;
			}
		} else if(isCamelKGroovyDSL(textDocumentItem, uri)) {
			CamelKGroovyDSLParser camelKGroovyDSLParser = new CamelKGroovyDSLParser();
			if (camelKGroovyDSLParser.getCorrespondingMethodName(textDocumentItem, line) != null) {
				return camelKGroovyDSLParser;
			}
		}
		return null;
	}
	
	private boolean isCamelKGroovyDSL(TextDocumentItem textDocumentItem, String uri) {
		//improve this method to provide better heuristic to detect if it is a Camel file or not
		return uri.endsWith(CAMELK_GROOVY_FILENAME_SUFFIX) || uri.endsWith(".groovy") && textDocumentItem.getText().startsWith(SHEBANG_CAMEL_K);
	}

	private boolean isCamelJavaDSL(TextDocumentItem textDocumentItem, String uri) {
		//improve this method to provide better heuristic to detect if it is a Camel file or not
		return uri.endsWith(".java") && textDocumentItem.getText().contains("camel");
	}

}
