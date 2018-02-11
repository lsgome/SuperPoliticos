import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { TipoAtributoSp } from './tipo-atributo-sp.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<TipoAtributoSp>;

@Injectable()
export class TipoAtributoSpService {

    private resourceUrl =  SERVER_API_URL + 'api/tipo-atributos';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/tipo-atributos';

    constructor(private http: HttpClient) { }

    create(tipoAtributo: TipoAtributoSp): Observable<EntityResponseType> {
        const copy = this.convert(tipoAtributo);
        return this.http.post<TipoAtributoSp>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(tipoAtributo: TipoAtributoSp): Observable<EntityResponseType> {
        const copy = this.convert(tipoAtributo);
        return this.http.put<TipoAtributoSp>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<TipoAtributoSp>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<TipoAtributoSp[]>> {
        const options = createRequestOption(req);
        return this.http.get<TipoAtributoSp[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<TipoAtributoSp[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<TipoAtributoSp[]>> {
        const options = createRequestOption(req);
        return this.http.get<TipoAtributoSp[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<TipoAtributoSp[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: TipoAtributoSp = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<TipoAtributoSp[]>): HttpResponse<TipoAtributoSp[]> {
        const jsonResponse: TipoAtributoSp[] = res.body;
        const body: TipoAtributoSp[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to TipoAtributoSp.
     */
    private convertItemFromServer(tipoAtributo: TipoAtributoSp): TipoAtributoSp {
        const copy: TipoAtributoSp = Object.assign({}, tipoAtributo);
        return copy;
    }

    /**
     * Convert a TipoAtributoSp to a JSON which can be sent to the server.
     */
    private convert(tipoAtributo: TipoAtributoSp): TipoAtributoSp {
        const copy: TipoAtributoSp = Object.assign({}, tipoAtributo);
        return copy;
    }
}
