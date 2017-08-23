package no.fint.model.relation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@EqualsAndHashCode
public class Relation {
    @Getter
    private String relationName;
    @Getter
    private String link;

    private Relation(String relationName, String link) {
        this.relationName = relationName;
        this.link = link;
    }

    public static class Builder {
        private String relationName;
        private String link;
        private Class<?> type;
        private String field;
        private String value;

        public Builder with(Enum<?> relation) {
            this.relationName = relation.name().toLowerCase();
            return this;
        }

        public Builder forType(Class<?> type) {
            this.type = type;
            return this;
        }

        public Builder field(String field) {
            this.field = field;
            return this;
        }

        public Builder value(String value) {
            this.value = value;
            return this;
        }

        public Builder link(String link) {
            this.link = link;
            return this;
        }

        public Relation build() {
            if (link == null || "".equals(link)) {
                if (type == null || value == null) {
                    throw new IllegalArgumentException("Missing value to create Relation, either link value is set, or type, field and value");
                }

                String link;
                if (field == null) {
                    link = String.format("${%s}/%s", getTypeString(type), value);
                } else {
                    link = String.format("${%s}/%s/%s", getTypeString(type), field, value);
                }
                return new Relation(relationName, link);
            } else {
                return new Relation(relationName, link);
            }
        }

        private String getTypeString(Class<?> clazz) {
            return clazz.getSimpleName().toLowerCase();
        }
    }
}
