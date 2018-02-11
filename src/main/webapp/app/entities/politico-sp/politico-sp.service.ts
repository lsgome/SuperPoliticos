import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { PoliticoSp } from './politico-sp.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<PoliticoSp>;

@Injectable()
export class PoliticoSpService {

    private resourceUrl =  SERVER_API_URL + 'api/politicos';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/politicos';

    constructor(private http: HttpClient) { }

    create(politico: PoliticoSp): Observable<EntityResponseType> {
        const copy = this.convert(politico);
        return this.http.post<PoliticoSp>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(politico: PoliticoSp): Observable<EntityResponseType> {
        const copy = this.convert(politico);
        return this.http.put<PoliticoSp>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<PoliticoSp>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<PoliticoSp[]>> {
        const options = createRequestOption(req);
        return this.http.get<PoliticoSp[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<PoliticoSp[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<PoliticoSp[]>> {
        const options = createRequestOption(req);
        return this.http.get<PoliticoSp[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<PoliticoSp[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: PoliticoSp = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<PoliticoSp[]>): HttpResponse<PoliticoSp[]> {
        const jsonResponse: PoliticoSp[] = res.body;
        const body: PoliticoSp[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to PoliticoSp.
     */
    private convertItemFromServer(politico: PoliticoSp): PoliticoSp {
        const copy: PoliticoSp = Object.assign({}, politico);
        return copy;
    }

    /**
     * Convert a PoliticoSp to a JSON which can be sent to the server.
     */
    private convert(politico: PoliticoSp): PoliticoSp {
        const copy: PoliticoSp = Object.assign({}, politico);
        return copy;
    }
}
