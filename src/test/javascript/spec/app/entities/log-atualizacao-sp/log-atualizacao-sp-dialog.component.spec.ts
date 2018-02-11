/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SuperPoliticosTestModule } from '../../../test.module';
import { LogAtualizacaoSpDialogComponent } from '../../../../../../main/webapp/app/entities/log-atualizacao-sp/log-atualizacao-sp-dialog.component';
import { LogAtualizacaoSpService } from '../../../../../../main/webapp/app/entities/log-atualizacao-sp/log-atualizacao-sp.service';
import { LogAtualizacaoSp } from '../../../../../../main/webapp/app/entities/log-atualizacao-sp/log-atualizacao-sp.model';

describe('Component Tests', () => {

    describe('LogAtualizacaoSp Management Dialog Component', () => {
        let comp: LogAtualizacaoSpDialogComponent;
        let fixture: ComponentFixture<LogAtualizacaoSpDialogComponent>;
        let service: LogAtualizacaoSpService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SuperPoliticosTestModule],
                declarations: [LogAtualizacaoSpDialogComponent],
                providers: [
                    LogAtualizacaoSpService
                ]
            })
            .overrideTemplate(LogAtualizacaoSpDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LogAtualizacaoSpDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LogAtualizacaoSpService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new LogAtualizacaoSp(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.logAtualizacao = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'logAtualizacaoListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new LogAtualizacaoSp();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.logAtualizacao = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'logAtualizacaoListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
