/*
 * Copyright 2012-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.boot.actuate.info;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link SimpleInfoContributor}.
 *
 * @author Stephane Nicoll
 */
public class SimpleInfoContributorTests {

	@Rule
	public final ExpectedException thrown = ExpectedException.none();

	@Test
	public void prefixIsMandatory() {
		this.thrown.expect(IllegalArgumentException.class);
		new SimpleInfoContributor(null, new Object());
	}

	@Test
	public void mapSimpleObject() {
		Object o = new Object();
		Info info = contributeFrom("test", o);
		assertThat(info.get("test")).isSameAs(o);
	}

	private static Info contributeFrom(String prefix, Object detail) {
		SimpleInfoContributor contributor = new SimpleInfoContributor(prefix, detail);
		Info.Builder builder = new Info.Builder();
		contributor.contribute(builder);
		return builder.build();
	}

}
