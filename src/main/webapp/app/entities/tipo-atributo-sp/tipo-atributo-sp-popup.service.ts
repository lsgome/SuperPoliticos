import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { TipoAtributoSp } from './tipo-atributo-sp.model';
import { TipoAtributoSpService } from './tipo-atributo-sp.service';

@Injectable()
export class TipoAtributoSpPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private tipoAtributoService: TipoAtributoSpService

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
                this.tipoAtributoService.find(id)
                    .subscribe((tipoAtributoResponse: HttpResponse<TipoAtributoSp>) => {
                        const tipoAtributo: TipoAtributoSp = tipoAtributoResponse.body;
                        this.ngbModalRef = this.tipoAtributoModalRef(component, tipoAtributo);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.tipoAtributoModalRef(component, new TipoAtributoSp());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    tipoAtributoModalRef(component: Component, tipoAtributo: TipoAtributoSp): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.tipoAtributo = tipoAtributo;
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
