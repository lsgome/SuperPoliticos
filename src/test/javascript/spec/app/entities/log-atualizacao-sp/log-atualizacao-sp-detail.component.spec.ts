/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SuperPoliticosTestModule } from '../../../test.module';
import { LogAtualizacaoSpDetailComponent } from '../../../../../../main/webapp/app/entities/log-atualizacao-sp/log-atualizacao-sp-detail.component';
import { LogAtualizacaoSpService } from '../../../../../../main/webapp/app/entities/log-atualizacao-sp/log-atualizacao-sp.service';
import { LogAtualizacaoSp } from '../../../../../../main/webapp/app/entities/log-atualizacao-sp/log-atualizacao-sp.model';

describe('Component Tests', () => {

    describe('LogAtualizacaoSp Management Detail Component', () => {
        let comp: LogAtualizacaoSpDetailComponent;
        let fixture: ComponentFixture<LogAtualizacaoSpDetailComponent>;
        let service: LogAtualizacaoSpService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SuperPoliticosTestModule],
                declarations: [LogAtualizacaoSpDetailComponent],
                providers: [
                    LogAtualizacaoSpService
                ]
            })
            .overrideTemplate(LogAtualizacaoSpDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LogAtualizacaoSpDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LogAtualizacaoSpService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new LogAtualizacaoSp(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.logAtualizacao).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
