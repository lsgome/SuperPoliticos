import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LogAtualizacaoSp } from './log-atualizacao-sp.model';
import { LogAtualizacaoSpPopupService } from './log-atualizacao-sp-popup.service';
import { LogAtualizacaoSpService } from './log-atualizacao-sp.service';

@Component({
    selector: 'jhi-log-atualizacao-sp-dialog',
    templateUrl: './log-atualizacao-sp-dialog.component.html'
})
export class LogAtualizacaoSpDialogComponent implements OnInit {

    logAtualizacao: LogAtualizacaoSp;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private logAtualizacaoService: LogAtualizacaoSpService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.logAtualizacao.id !== undefined) {
            this.subscribeToSaveResponse(
                this.logAtualizacaoService.update(this.logAtualizacao));
        } else {
            this.subscribeToSaveResponse(
                this.logAtualizacaoService.create(this.logAtualizacao));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<LogAtualizacaoSp>>) {
        result.subscribe((res: HttpResponse<LogAtualizacaoSp>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: LogAtualizacaoSp) {
        this.eventManager.broadcast({ name: 'logAtualizacaoListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-log-atualizacao-sp-popup',
    template: ''
})
export class LogAtualizacaoSpPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private logAtualizacaoPopupService: LogAtualizacaoSpPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.logAtualizacaoPopupService
                    .open(LogAtualizacaoSpDialogComponent as Component, params['id']);
            } else {
                this.logAtualizacaoPopupService
                    .open(LogAtualizacaoSpDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
