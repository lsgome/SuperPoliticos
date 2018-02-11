/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SuperPoliticosTestModule } from '../../../test.module';
import { LogAtualizacaoSpDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/log-atualizacao-sp/log-atualizacao-sp-delete-dialog.component';
import { LogAtualizacaoSpService } from '../../../../../../main/webapp/app/entities/log-atualizacao-sp/log-atualizacao-sp.service';

describe('Component Tests', () => {

    describe('LogAtualizacaoSp Management Delete Component', () => {
        let comp: LogAtualizacaoSpDeleteDialogComponent;
        let fixture: ComponentFixture<LogAtualizacaoSpDeleteDialogComponent>;
        let service: LogAtualizacaoSpService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SuperPoliticosTestModule],
                declarations: [LogAtualizacaoSpDeleteDialogComponent],
                providers: [
                    LogAtualizacaoSpService
                ]
            })
            .overrideTemplate(LogAtualizacaoSpDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LogAtualizacaoSpDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LogAtualizacaoSpService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
