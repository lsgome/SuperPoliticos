/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SuperPoliticosTestModule } from '../../../test.module';
import { LogAtualizacaoSpComponent } from '../../../../../../main/webapp/app/entities/log-atualizacao-sp/log-atualizacao-sp.component';
import { LogAtualizacaoSpService } from '../../../../../../main/webapp/app/entities/log-atualizacao-sp/log-atualizacao-sp.service';
import { LogAtualizacaoSp } from '../../../../../../main/webapp/app/entities/log-atualizacao-sp/log-atualizacao-sp.model';

describe('Component Tests', () => {

    describe('LogAtualizacaoSp Management Component', () => {
        let comp: LogAtualizacaoSpComponent;
        let fixture: ComponentFixture<LogAtualizacaoSpComponent>;
        let service: LogAtualizacaoSpService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SuperPoliticosTestModule],
                declarations: [LogAtualizacaoSpComponent],
                providers: [
                    LogAtualizacaoSpService
                ]
            })
            .overrideTemplate(LogAtualizacaoSpComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LogAtualizacaoSpComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LogAtualizacaoSpService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new LogAtualizacaoSp(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.logAtualizacaos[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
