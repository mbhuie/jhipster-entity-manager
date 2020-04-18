package com.msb.demo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.msb.demo.web.rest.TestUtil;

public class IndexETFTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IndexETF.class);
        IndexETF indexETF1 = new IndexETF();
        indexETF1.setId(1L);
        IndexETF indexETF2 = new IndexETF();
        indexETF2.setId(indexETF1.getId());
        assertThat(indexETF1).isEqualTo(indexETF2);
        indexETF2.setId(2L);
        assertThat(indexETF1).isNotEqualTo(indexETF2);
        indexETF1.setId(null);
        assertThat(indexETF1).isNotEqualTo(indexETF2);
    }
}
