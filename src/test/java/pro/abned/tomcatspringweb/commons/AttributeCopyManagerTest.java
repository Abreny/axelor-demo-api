package pro.abned.tomcatspringweb.commons;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AttributeCopyManagerTest {
    @Test
    void testCopyNotNull() {
        Bean1 bean1 = new Bean1();
        bean1.attr1 = "Hello";
        bean1.attr2 = null;

        Bean2 bean2 = new Bean2();
        bean2.attr2 = "world";

        AttributeCopyManager.copyNotNull(bean1, bean2);

        assertThat(bean2.attr1).isEqualTo("Hello");
        assertThat(bean2.attr2).isEqualTo("world");
    }

    @Test
    void testCopyNot() {
        Bean1 bean1 = new Bean1();
        bean1.attr1 = "Hello";
        bean1.attr2 = null;

        Bean2 bean2 = new Bean2();
        bean2.attr2 = "world";

        AttributeCopyManager.copyNot(bean1, bean2);

        assertThat(bean2.attr1).isEqualTo("Hello");
        assertThat(bean2.attr2).isNull();
    }

    @Test
    void testCopyNot_giving_attribute() {
        Bean1 bean1 = new Bean1();
        bean1.attr1 = "Hello";
        bean1.attr2 = null;

        Bean2 bean2 = new Bean2();
        bean2.attr2 = "world";

        AttributeCopyManager.copyNot(bean1, bean2, "attr2");

        assertThat(bean2.attr1).isEqualTo("Hello");
        assertThat(bean2.attr2).isEqualTo("world");
    }

    private static class Bean1 {
        public String attr1;
        public String attr2;

        public String getAttr1() {
            return attr1;
        }

        public void setAttr1(String attr1) {
            this.attr1 = attr1;
        }

        public String getAttr2() {
            return attr2;
        }

        public void setAttr2(String attr2) {
            this.attr2 = attr2;
        }
    }

    private static class Bean2 {
        public String attr1;
        public String attr2;

        public String attr3;

        public String getAttr1() {
            return attr1;
        }

        public void setAttr1(String attr1) {
            this.attr1 = attr1;
        }

        public String getAttr2() {
            return attr2;
        }

        public void setAttr2(String attr2) {
            this.attr2 = attr2;
        }
    }
}