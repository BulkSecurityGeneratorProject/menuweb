import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Plat } from './plat.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Plat>;

@Injectable()
export class PlatService {

    private resourceUrl =  SERVER_API_URL + 'api/plats';

    constructor(private http: HttpClient) { }

    create(plat: Plat): Observable<EntityResponseType> {
        const copy = this.convert(plat);
        return this.http.post<Plat>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(plat: Plat): Observable<EntityResponseType> {
        const copy = this.convert(plat);
        return this.http.put<Plat>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Plat>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Plat[]>> {
        const options = createRequestOption(req);
        return this.http.get<Plat[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Plat[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Plat = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Plat[]>): HttpResponse<Plat[]> {
        const jsonResponse: Plat[] = res.body;
        const body: Plat[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Plat.
     */
    private convertItemFromServer(plat: Plat): Plat {
        const copy: Plat = Object.assign({}, plat);
        return copy;
    }

    /**
     * Convert a Plat to a JSON which can be sent to the server.
     */
    private convert(plat: Plat): Plat {
        const copy: Plat = Object.assign({}, plat);
        return copy;
    }
}
