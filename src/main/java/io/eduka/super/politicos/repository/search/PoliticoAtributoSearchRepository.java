package io.eduka.super.politicos.repository.search;

import io.eduka.super.politicos.domain.PoliticoAtributo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PoliticoAtributo entity.
 */
public interface PoliticoAtributoSearchRepository extends ElasticsearchRepository<PoliticoAtributo, Long> {
}
