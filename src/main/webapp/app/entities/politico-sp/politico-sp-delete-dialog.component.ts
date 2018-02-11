import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PoliticoSp } from './politico-sp.model';
import { PoliticoSpPopupService } from './politico-sp-popup.service';
import { PoliticoSpService } from './politico-sp.service';

@Component({
    selector: 'jhi-politico-sp-delete-dialog',
    templateUrl: './politico-sp-delete-dialog.component.html'
})
export class PoliticoSpDeleteDialogComponent {

    politico: PoliticoSp;

    constructor(
        private politicoService: PoliticoSpService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.politicoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'politicoListModification',
                content: 'Deleted an politico'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-politico-sp-delete-popup',
    template: ''
})
export class PoliticoSpDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private politicoPopupService: PoliticoSpPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.politicoPopupService
                .open(PoliticoSpDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
