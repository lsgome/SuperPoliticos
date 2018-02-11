package io.eduka.super.politicos.repository.search;

import io.eduka.super.politicos.domain.Politico;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Politico entity.
 */
public interface PoliticoSearchRepository extends ElasticsearchRepository<Politico, Long> {
}
