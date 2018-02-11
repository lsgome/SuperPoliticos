import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LogAtualizacaoSp } from './log-atualizacao-sp.model';
import { LogAtualizacaoSpPopupService } from './log-atualizacao-sp-popup.service';
import { LogAtualizacaoSpService } from './log-atualizacao-sp.service';

@Component({
    selector: 'jhi-log-atualizacao-sp-delete-dialog',
    templateUrl: './log-atualizacao-sp-delete-dialog.component.html'
})
export class LogAtualizacaoSpDeleteDialogComponent {

    logAtualizacao: LogAtualizacaoSp;

    constructor(
        private logAtualizacaoService: LogAtualizacaoSpService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.logAtualizacaoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'logAtualizacaoListModification',
                content: 'Deleted an logAtualizacao'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-log-atualizacao-sp-delete-popup',
    template: ''
})
export class LogAtualizacaoSpDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private logAtualizacaoPopupService: LogAtualizacaoSpPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.logAtualizacaoPopupService
                .open(LogAtualizacaoSpDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
