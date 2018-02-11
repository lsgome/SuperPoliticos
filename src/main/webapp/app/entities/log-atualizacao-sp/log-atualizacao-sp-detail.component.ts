import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { LogAtualizacaoSp } from './log-atualizacao-sp.model';
import { LogAtualizacaoSpService } from './log-atualizacao-sp.service';

@Component({
    selector: 'jhi-log-atualizacao-sp-detail',
    templateUrl: './log-atualizacao-sp-detail.component.html'
})
export class LogAtualizacaoSpDetailComponent implements OnInit, OnDestroy {

    logAtualizacao: LogAtualizacaoSp;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private logAtualizacaoService: LogAtualizacaoSpService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLogAtualizacaos();
    }

    load(id) {
        this.logAtualizacaoService.find(id)
            .subscribe((logAtualizacaoResponse: HttpResponse<LogAtualizacaoSp>) => {
                this.logAtualizacao = logAtualizacaoResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLogAtualizacaos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'logAtualizacaoListModification',
            (response) => this.load(this.logAtualizacao.id)
        );
    }
}
