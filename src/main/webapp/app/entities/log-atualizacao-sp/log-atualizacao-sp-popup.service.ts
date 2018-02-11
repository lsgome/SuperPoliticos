import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { LogAtualizacaoSp } from './log-atualizacao-sp.model';
import { LogAtualizacaoSpService } from './log-atualizacao-sp.service';

@Injectable()
export class LogAtualizacaoSpPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private logAtualizacaoService: LogAtualizacaoSpService

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
                this.logAtualizacaoService.find(id)
                    .subscribe((logAtualizacaoResponse: HttpResponse<LogAtualizacaoSp>) => {
                        const logAtualizacao: LogAtualizacaoSp = logAtualizacaoResponse.body;
                        logAtualizacao.data = this.datePipe
                            .transform(logAtualizacao.data, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.logAtualizacaoModalRef(component, logAtualizacao);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.logAtualizacaoModalRef(component, new LogAtualizacaoSp());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    logAtualizacaoModalRef(component: Component, logAtualizacao: LogAtualizacaoSp): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.logAtualizacao = logAtualizacao;
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
