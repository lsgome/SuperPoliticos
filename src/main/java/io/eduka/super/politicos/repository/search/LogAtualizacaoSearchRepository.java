package io.eduka.super.politicos.repository.search;

import io.eduka.super.politicos.domain.LogAtualizacao;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the LogAtualizacao entity.
 */
public interface LogAtualizacaoSearchRepository extends ElasticsearchRepository<LogAtualizacao, Long> {
}
