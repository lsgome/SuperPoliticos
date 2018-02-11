import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PoliticoAtributoSp } from './politico-atributo-sp.model';
import { PoliticoAtributoSpService } from './politico-atributo-sp.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-politico-atributo-sp',
    templateUrl: './politico-atributo-sp.component.html'
})
export class PoliticoAtributoSpComponent implements OnInit, OnDestroy {
politicoAtributos: PoliticoAtributoSp[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private politicoAtributoService: PoliticoAtributoSpService,
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
            this.politicoAtributoService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: HttpResponse<PoliticoAtributoSp[]>) => this.politicoAtributos = res.body,
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
       }
        this.politicoAtributoService.query().subscribe(
            (res: HttpResponse<PoliticoAtributoSp[]>) => {
                this.politicoAtributos = res.body;
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
        this.registerChangeInPoliticoAtributos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PoliticoAtributoSp) {
        return item.id;
    }
    registerChangeInPoliticoAtributos() {
        this.eventSubscriber = this.eventManager.subscribe('politicoAtributoListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
