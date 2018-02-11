import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { PoliticoAtributoSp } from './politico-atributo-sp.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<PoliticoAtributoSp>;

@Injectable()
export class PoliticoAtributoSpService {

    private resourceUrl =  SERVER_API_URL + 'api/politico-atributos';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/politico-atributos';

    constructor(private http: HttpClient) { }

    create(politicoAtributo: PoliticoAtributoSp): Observable<EntityResponseType> {
        const copy = this.convert(politicoAtributo);
        return this.http.post<PoliticoAtributoSp>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(politicoAtributo: PoliticoAtributoSp): Observable<EntityResponseType> {
        const copy = this.convert(politicoAtributo);
        return this.http.put<PoliticoAtributoSp>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<PoliticoAtributoSp>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<PoliticoAtributoSp[]>> {
        const options = createRequestOption(req);
        return this.http.get<PoliticoAtributoSp[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<PoliticoAtributoSp[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<PoliticoAtributoSp[]>> {
        const options = createRequestOption(req);
        return this.http.get<PoliticoAtributoSp[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<PoliticoAtributoSp[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: PoliticoAtributoSp = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<PoliticoAtributoSp[]>): HttpResponse<PoliticoAtributoSp[]> {
        const jsonResponse: PoliticoAtributoSp[] = res.body;
        const body: PoliticoAtributoSp[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to PoliticoAtributoSp.
     */
    private convertItemFromServer(politicoAtributo: PoliticoAtributoSp): PoliticoAtributoSp {
        const copy: PoliticoAtributoSp = Object.assign({}, politicoAtributo);
        return copy;
    }

    /**
     * Convert a PoliticoAtributoSp to a JSON which can be sent to the server.
     */
    private convert(politicoAtributo: PoliticoAtributoSp): PoliticoAtributoSp {
        const copy: PoliticoAtributoSp = Object.assign({}, politicoAtributo);
        return copy;
    }
}
