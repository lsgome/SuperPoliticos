import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { LogAtualizacaoSp } from './log-atualizacao-sp.model';
import { LogAtualizacaoSpService } from './log-atualizacao-sp.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-log-atualizacao-sp',
    templateUrl: './log-atualizacao-sp.component.html'
})
export class LogAtualizacaoSpComponent implements OnInit, OnDestroy {
logAtualizacaos: LogAtualizacaoSp[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private logAtualizacaoService: LogAtualizacaoSpService,
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
            this.logAtualizacaoService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: HttpResponse<LogAtualizacaoSp[]>) => this.logAtualizacaos = res.body,
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
       }
        this.logAtualizacaoService.query().subscribe(
            (res: HttpResponse<LogAtualizacaoSp[]>) => {
                this.logAtualizacaos = res.body;
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
        this.registerChangeInLogAtualizacaos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: LogAtualizacaoSp) {
        return item.id;
    }
    registerChangeInLogAtualizacaos() {
        this.eventSubscriber = this.eventManager.subscribe('logAtualizacaoListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
