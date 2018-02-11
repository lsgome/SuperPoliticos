import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { LogAtualizacaoSp } from './log-atualizacao-sp.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<LogAtualizacaoSp>;

@Injectable()
export class LogAtualizacaoSpService {

    private resourceUrl =  SERVER_API_URL + 'api/log-atualizacaos';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/log-atualizacaos';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(logAtualizacao: LogAtualizacaoSp): Observable<EntityResponseType> {
        const copy = this.convert(logAtualizacao);
        return this.http.post<LogAtualizacaoSp>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(logAtualizacao: LogAtualizacaoSp): Observable<EntityResponseType> {
        const copy = this.convert(logAtualizacao);
        return this.http.put<LogAtualizacaoSp>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<LogAtualizacaoSp>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<LogAtualizacaoSp[]>> {
        const options = createRequestOption(req);
        return this.http.get<LogAtualizacaoSp[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<LogAtualizacaoSp[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<LogAtualizacaoSp[]>> {
        const options = createRequestOption(req);
        return this.http.get<LogAtualizacaoSp[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<LogAtualizacaoSp[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: LogAtualizacaoSp = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<LogAtualizacaoSp[]>): HttpResponse<LogAtualizacaoSp[]> {
        const jsonResponse: LogAtualizacaoSp[] = res.body;
        const body: LogAtualizacaoSp[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to LogAtualizacaoSp.
     */
    private convertItemFromServer(logAtualizacao: LogAtualizacaoSp): LogAtualizacaoSp {
        const copy: LogAtualizacaoSp = Object.assign({}, logAtualizacao);
        copy.data = this.dateUtils
            .convertDateTimeFromServer(logAtualizacao.data);
        return copy;
    }

    /**
     * Convert a LogAtualizacaoSp to a JSON which can be sent to the server.
     */
    private convert(logAtualizacao: LogAtualizacaoSp): LogAtualizacaoSp {
        const copy: LogAtualizacaoSp = Object.assign({}, logAtualizacao);

        copy.data = this.dateUtils.toDate(logAtualizacao.data);
        return copy;
    }
}
