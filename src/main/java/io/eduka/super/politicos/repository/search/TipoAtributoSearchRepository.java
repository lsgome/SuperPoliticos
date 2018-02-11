package io.eduka.super.politicos.repository.search;

import io.eduka.super.politicos.domain.TipoAtributo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TipoAtributo entity.
 */
public interface TipoAtributoSearchRepository extends ElasticsearchRepository<TipoAtributo, Long> {
}
