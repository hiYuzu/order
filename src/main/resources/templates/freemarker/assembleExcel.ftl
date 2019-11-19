<?xml version="1.0"?>
<?mso-application progid="Excel.Sheet"?>
<Workbook xmlns="urn:schemas-microsoft-com:office:spreadsheet"
 xmlns:o="urn:schemas-microsoft-com:office:office"
 xmlns:x="urn:schemas-microsoft-com:office:excel"
 xmlns:ss="urn:schemas-microsoft-com:office:spreadsheet"
 xmlns:html="http://www.w3.org/TR/REC-html40">
 <DocumentProperties xmlns="urn:schemas-microsoft-com:office:office">
  <Created>2006-09-16T00:00:00Z</Created>
  <LastSaved>2019-04-04T07:39:41Z</LastSaved>
  <Version>15.00</Version>
 </DocumentProperties>
 <OfficeDocumentSettings xmlns="urn:schemas-microsoft-com:office:office">
  <AllowPNG/>
  <RemovePersonalInformation/>
 </OfficeDocumentSettings>
 <ExcelWorkbook xmlns="urn:schemas-microsoft-com:office:excel">
  <WindowHeight>8010</WindowHeight>
  <WindowWidth>14810</WindowWidth>
  <WindowTopX>240</WindowTopX>
  <WindowTopY>110</WindowTopY>
  <ProtectStructure>False</ProtectStructure>
  <ProtectWindows>False</ProtectWindows>
 </ExcelWorkbook>
 <Styles>
  <Style ss:ID="Default" ss:Name="Normal">
   <Alignment ss:Vertical="Bottom"/>
   <Borders/>
   <Font ss:FontName="宋体" x:CharSet="134" ss:Size="11" ss:Color="#000000"/>
   <Interior/>
   <NumberFormat/>
   <Protection/>
  </Style>
  <Style ss:ID="s62">
   <Alignment ss:Horizontal="Center" ss:Vertical="Bottom"/>
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="1"/>
   </Borders>
   <Interior ss:Color="#D9D9D9" ss:Pattern="Solid"/>
  </Style>
  <Style ss:ID="s63">
   <Alignment ss:Horizontal="Center" ss:Vertical="Bottom" ss:ShrinkToFit="1"/>
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="1"/>
   </Borders>
   <NumberFormat ss:Format="@"/>
  </Style>
  <Style ss:ID="s67">
   <Alignment ss:Horizontal="Center" ss:Vertical="Bottom"/>
   <Font ss:FontName="宋体" x:CharSet="134" ss:Size="20" ss:Color="#000000"/>
  </Style>
 </Styles>
 <Worksheet ss:Name="组装记录">
  <Table ss:ExpandedColumnCount="8" x:FullColumns="1"
   x:FullRows="1" ss:DefaultRowHeight="14">
   <Column ss:AutoFitWidth="0" ss:Width="111"/>
   <Column ss:AutoFitWidth="0" ss:Width="75"/>
   <Column ss:AutoFitWidth="0" ss:Width="97"/>
   <Column ss:Width="116" ss:Span="2"/>
   <Column ss:Index="7" ss:Width="86.5"/>
   <Column ss:AutoFitWidth="0" ss:Width="163.5"/>
   <Row>
    <Cell ss:MergeAcross="7" ss:MergeDown="1" ss:StyleID="s67"><Data
      ss:Type="String">组装记录列表</Data></Cell>
   </Row>
   <Row ss:Index="3">
    <Cell ss:StyleID="s62"><Data ss:Type="String">P/N</Data></Cell>
    <Cell ss:StyleID="s62"><Data ss:Type="String">S/N</Data></Cell>
    <Cell ss:StyleID="s62"><Data ss:Type="String">组装单状态</Data></Cell>
    <Cell ss:StyleID="s62"><Data ss:Type="String">生产工作单</Data></Cell>
    <Cell ss:StyleID="s62"><Data ss:Type="String">关键部件</Data></Cell>
    <Cell ss:StyleID="s62"><Data ss:Type="String">完成时间</Data></Cell>
    <Cell ss:StyleID="s62"><Data ss:Type="String">组装单备注</Data></Cell>
    <Cell ss:StyleID="s62"><Data ss:Type="String">部件列表</Data></Cell>
   </Row>
   <#if (assembleModelList?size>0) >
		<#list assembleModelList as itemValue>
		   <Row ss:AutoFitHeight="0">
			<Cell ss:StyleID="s63"><Data ss:Type="String">${itemValue.pnName!}</Data></Cell>
			<Cell ss:StyleID="s63"><Data ss:Type="String">${itemValue.snCode!}</Data></Cell>
			<Cell ss:StyleID="s63"><Data ss:Type="String">${itemValue.assembleStatusName!}</Data></Cell>
			<Cell ss:StyleID="s63"><Data ss:Type="String">${itemValue.jobNo!}</Data></Cell>
			<Cell ss:StyleID="s63"><Data ss:Type="String">${itemValue.cruxNo!}</Data></Cell>
			<Cell ss:StyleID="s63"><Data ss:Type="String">${itemValue.completeDate!}</Data></Cell>
			<Cell ss:StyleID="s63"><Data ss:Type="String">${itemValue.assembleMemo!}</Data></Cell>
			<Cell ss:StyleID="s63"><Data ss:Type="String">${itemValue.partsModelString!}</Data></Cell>
		   </Row>
	   </#list>
	</#if>
    <#if (assembleModelList?size==0) >
	   <Row ss:AutoFitHeight="0">
		<Cell ss:StyleID="s63"/>
		<Cell ss:StyleID="s63"/>
		<Cell ss:StyleID="s63"/>
		<Cell ss:StyleID="s63"/>
		<Cell ss:StyleID="s63"/>
		<Cell ss:StyleID="s63"/>
		<Cell ss:StyleID="s63"/>
		<Cell ss:StyleID="s63"/>
	   </Row>
    </#if>
  </Table>
  <WorksheetOptions xmlns="urn:schemas-microsoft-com:office:excel">
   <PageSetup>
    <Header x:Margin="0.3"/>
    <Footer x:Margin="0.3"/>
    <PageMargins x:Bottom="0.75" x:Left="0.7" x:Right="0.7" x:Top="0.75"/>
   </PageSetup>
   <Print>
    <ValidPrinterInfo/>
    <PaperSizeIndex>9</PaperSizeIndex>
    <HorizontalResolution>600</HorizontalResolution>
    <VerticalResolution>600</VerticalResolution>
   </Print>
   <Selected/>
   <FreezePanes/>
   <FrozenNoSplit/>
   <SplitHorizontal>3</SplitHorizontal>
   <TopRowBottomPane>3</TopRowBottomPane>
   <ActivePane>2</ActivePane>
   <Panes>
    <Pane>
     <Number>3</Number>
    </Pane>
    <Pane>
     <Number>2</Number>
     <ActiveRow>6</ActiveRow>
     <ActiveCol>7</ActiveCol>
    </Pane>
   </Panes>
   <ProtectObjects>False</ProtectObjects>
   <ProtectScenarios>False</ProtectScenarios>
  </WorksheetOptions>
 </Worksheet>
</Workbook>
