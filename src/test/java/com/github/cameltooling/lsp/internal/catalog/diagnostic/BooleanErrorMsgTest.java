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
package com.github.cameltooling.lsp.internal.catalog.diagnostic;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

class BooleanErrorMsgTest {

	@Test
	void testGetErrorMessageSpecifyInvalid() throws Exception {
		Map<String, String> map = new HashMap<>();
		map.put("aKey", "aNonBooleanValue");
		assertThat(new BooleanErrorMsg().getErrorMessage(map.entrySet().iterator().next()))
			.contains("Invalid")
			.contains("aNonBooleanValue");
	}

	@Test
	void testGetErrorMessageSpecifyEmpty() throws Exception {
		Map<String, String> map = new HashMap<>();
		map.put("aKey", "");
		assertThat(new BooleanErrorMsg().getErrorMessage(map.entrySet().iterator().next())).contains("Empty");
	}

}
