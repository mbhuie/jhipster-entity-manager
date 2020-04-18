package com.msb.demo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.msb.demo.web.rest.TestUtil;

public class ConstituentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Constituent.class);
        Constituent constituent1 = new Constituent();
        constituent1.setId(1L);
        Constituent constituent2 = new Constituent();
        constituent2.setId(constituent1.getId());
        assertThat(constituent1).isEqualTo(constituent2);
        constituent2.setId(2L);
        assertThat(constituent1).isNotEqualTo(constituent2);
        constituent1.setId(null);
        assertThat(constituent1).isNotEqualTo(constituent2);
    }
}
