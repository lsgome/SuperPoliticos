import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { PoliticoAtributoSp } from './politico-atributo-sp.model';
import { PoliticoAtributoSpService } from './politico-atributo-sp.service';

@Injectable()
export class PoliticoAtributoSpPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private politicoAtributoService: PoliticoAtributoSpService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.politicoAtributoService.find(id)
                    .subscribe((politicoAtributoResponse: HttpResponse<PoliticoAtributoSp>) => {
                        const politicoAtributo: PoliticoAtributoSp = politicoAtributoResponse.body;
                        this.ngbModalRef = this.politicoAtributoModalRef(component, politicoAtributo);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.politicoAtributoModalRef(component, new PoliticoAtributoSp());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    politicoAtributoModalRef(component: Component, politicoAtributo: PoliticoAtributoSp): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.politicoAtributo = politicoAtributo;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
