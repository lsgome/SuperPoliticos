import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { TipoAtributoSp } from './tipo-atributo-sp.model';
import { TipoAtributoSpService } from './tipo-atributo-sp.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-tipo-atributo-sp',
    templateUrl: './tipo-atributo-sp.component.html'
})
export class TipoAtributoSpComponent implements OnInit, OnDestroy {
tipoAtributos: TipoAtributoSp[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private tipoAtributoService: TipoAtributoSpService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search'] ?
            this.activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.tipoAtributoService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: HttpResponse<TipoAtributoSp[]>) => this.tipoAtributos = res.body,
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
       }
        this.tipoAtributoService.query().subscribe(
            (res: HttpResponse<TipoAtributoSp[]>) => {
                this.tipoAtributos = res.body;
                this.currentSearch = '';
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInTipoAtributos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: TipoAtributoSp) {
        return item.id;
    }
    registerChangeInTipoAtributos() {
        this.eventSubscriber = this.eventManager.subscribe('tipoAtributoListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
